#!/usr/bin/env python3
"""Compile and run the repository's automated verification checks."""

from pathlib import Path
import os
import re
import shutil
import subprocess
import sys
import tempfile


ROOT = Path(__file__).resolve().parent
MINIMUM_JAVA_VERSION = 21
JDBC_JAR = ROOT / "src" / "lib" / "sqlite-jdbc-3.51.3.0.jar"
TEST_RUNNER = "VerificationTests"


def run(command, *, cwd, label, timeout=180):
    print(f"\n== {label} ==")
    try:
        result = subprocess.run(command, cwd=cwd, check=False, timeout=timeout)
    except subprocess.TimeoutExpired:
        print(f"FAILED: {label} exceeded {timeout} seconds.", file=sys.stderr)
        return False
    if result.returncode != 0:
        print(f"FAILED: {label} exited with status {result.returncode}.", file=sys.stderr)
        return False
    return True


def tool_major_version(tool, arguments):
    try:
        result = subprocess.run(
            [tool, *arguments],
            check=False,
            capture_output=True,
            text=True,
            timeout=15,
        )
    except (OSError, subprocess.TimeoutExpired) as error:
        print(f"FAILED: Could not query {tool}: {error}", file=sys.stderr)
        return None

    version_text = result.stdout + result.stderr
    match = re.search(r"\b(\d+)(?:\.\d+)*", version_text)
    if result.returncode != 0 or match is None:
        print(f"FAILED: Could not parse {tool} version:\n{version_text}", file=sys.stderr)
        return None
    return int(match.group(1))


def main():
    if sys.version_info < (3, 8):
        print("FAILED: Python 3.8 or newer is required.", file=sys.stderr)
        return 1

    javac = shutil.which("javac")
    java = shutil.which("java")
    javadoc = shutil.which("javadoc")
    if not javac or not java or not javadoc:
        print("FAILED: A JDK with javac, java, and javadoc on PATH is required.", file=sys.stderr)
        return 1
    if not JDBC_JAR.is_file():
        print(f"FAILED: SQLite JDBC driver not found: {JDBC_JAR}", file=sys.stderr)
        return 1

    compiler_version = tool_major_version(javac, ["-version"])
    runtime_version = tool_major_version(java, ["-version"])
    if compiler_version is None or runtime_version is None:
        return 1
    print(f"Python {sys.version_info.major}.{sys.version_info.minor}; javac {compiler_version}; java {runtime_version}")
    if compiler_version < MINIMUM_JAVA_VERSION or runtime_version < MINIMUM_JAVA_VERSION:
        print(
            f"FAILED: Java {MINIMUM_JAVA_VERSION} or newer is required "
            f"(javac {compiler_version}, java {runtime_version}).",
            file=sys.stderr,
        )
        return 1

    source_files = sorted((ROOT / "src").rglob("*.java"))
    test_files = sorted((ROOT / "tests").rglob("*.java"))
    if not source_files or not test_files or not any(
        path.name == f"{TEST_RUNNER}.java" for path in test_files
    ):
        print("FAILED: Java production sources or the verification test runner are missing.", file=sys.stderr)
        return 1

    with tempfile.TemporaryDirectory(prefix="dungeon-verify-") as temporary_directory:
        temporary_root = Path(temporary_directory)
        classes_directory = temporary_root / "classes"
        test_working_directory = temporary_root / "work"
        docs_directory = temporary_root / "docs"
        classes_directory.mkdir()
        test_working_directory.mkdir()

        compile_command = [
            javac,
            "--release",
            str(MINIMUM_JAVA_VERSION),
            "-encoding",
            "UTF-8",
            "-classpath",
            str(JDBC_JAR),
            "-d",
            str(classes_directory),
            *map(str, source_files + test_files),
        ]
        if not run(compile_command, cwd=ROOT, label="Compile all production and test sources"):
            return 1

        test_classpath = os.pathsep.join((str(classes_directory), str(JDBC_JAR)))
        test_command = [java, "-classpath", test_classpath, TEST_RUNNER]
        if not run(test_command, cwd=test_working_directory, label="Run Java regression and SQLite integration tests"):
            return 1

        docs_command = [
            javadoc,
            "-quiet",
            "--release",
            str(MINIMUM_JAVA_VERSION),
            "-encoding",
            "UTF-8",
            "-d",
            str(docs_directory),
            "-sourcepath",
            str(ROOT / "src"),
            "-subpackages",
            "characters:dungeon:items:lib",
            "-classpath",
            str(JDBC_JAR),
        ]
        if not run(docs_command, cwd=ROOT, label="Generate Javadocs"):
            return 1

    print("\nAll verification checks passed.")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
