# C/C++ very basic support for IntelliJ IDEA CE

... or how to make an IJ IDEA a little VSCode...

## Includes
- making new C or C++ file (e.g., in empty project)
- syntax highlighting is actually provided by IJ itself;
- refactoring, code analysis, advanced types of highlighting is provided with Language Server Protocol via lsp4ij plugin from Redhat.

## Not (yet) implemented
- CMake, make, ninja any project build support (just use Terminal...);
- STM32 (or any other embedded C) projects
- choosing various language standards for Clangd
- any additional checks except the clangd provides by default;

## Additional requirements
- clangd visible through PATH
- clang/clang++ for compiling projects manually

## How to start

Install plugin >> Restart IDE

Empty project >> New file >> New C (or C++) file

Enjoy!
