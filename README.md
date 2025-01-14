<img src="https://www.yegor256.com/images/books/elegant-objects/cactus.svg" height="100px"  alt="eolang icon"/>

[![Gradle Build](https://github.com/polystat/j2eo/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/polystat/j2eo/actions/workflows/gradle-build.yml)
![LINE](https://img.shields.io/badge/line--coverage-7.15%25-red.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-1.22%25-red.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-14.94-yellow.svg)

[![Hits-of-Code](https://hitsofcode.com/github/polystat/j2eo)](https://hitsofcode.com/view/github/polystat/j2eo)
![Lines of code](https://img.shields.io/tokei/lines/github/polystat/j2eo)

This is a translator of **Java** programming language to [EOLANG](https://www.eolang.org) programming language.

## Usage

1. Make sure you have installed:
    - **Java 11+** (make sure command `java -version` shows 11+ version of Java in terminal if you have multiple Java
      version installed).
    - **Gradle 7.4+** to build the project.
    - **Maven 3.8+** to run tests (be aware of [possible conflicts](https://bugs.debian.org/cgi-bin/bugreport.cgi?bug=980467) of the
      latest versions of Maven and Java on some OSs).
    - **ANTLR4 4.10.1** to build parser.

   You can refer to ACCEPTANCE.md file for instructions on installing these components.

2. Clone the repo into your folder:

   HTTPS:
    ```shell
    git clone https://github.com/polystat/j2eo.git
    cd ./j2eo
    ```
   or SSH:
    ```shell
    git clone git@github.com:polystat/j2eo.git
    cd ./j2eo
    ```
3. Build the project:
    ```shell
    ./build.sh
    ```
    - See the [troubleshooting section](./README.md#troubleshooting) in case of problems
4. After build process, **j2eo.jar** file will appear in the project root folder (`./j2eo`). With this file, is it
   possible to translate **.java** files or packages to **.eo** packages. Run:

    ```shell
    java -jar j2eo.jar <source .java file or directory with Java files> -o <output directory>
    ```

   to get the translation.

   For example,
    ```shell
    java -jar j2eo.jar src/test/resources/SimpleTest.java -o output_eo
    ```
   or
    ```shell
    java -jar j2eo.jar src/test/resources/polystat_tests/test1 -o output_eo
    ```

You can also use [yegor256/j2eo](https://hub.docker.com/r/yegor256/j2eo) image for [Docker](https://docs.docker.com/get-docker/):

```
$ docker run -v $(pwd):/eo yegor256/j2eo hello.java --target output
```

This command will translate `hello.java` in the current directory, saving the output to `output/` subdirectory.

### Unit tests

Built-in unit tests may be executed using:

```shell
gradle test
```

### Bundled test suite

J2EO comes with 1000+ bundled tests. These tests are used with parallel execution:
- original Java source code of the text is compiled with Java compiler and executed. Stdout output is saved.
- Java source code is translated to EO using J2EO project, then compiled with EO compiler and executed. Stdout output is stored.
- Stdout outputs are compared. If they match — test is passed. If not — test is failed.

Test suite follows the Java Language Specification structure, covering applicable chapters and sections of the Java specifications.

Bundled tests may be executed using `./test_candidates.sh` script.

### Running translator on Hadoop

Hadoop is a large Java project (contains ~1.8M lines of code as of time of writing this). We included it as a benchmark of the translator.

Repository contains a script to build J2EO, download Hadoop repo and run J2EO on it.

Usage:

```shell
./test-hadoop.sh
```

It will download zipped `hadoop` and unpack it (in a separate folder) into `../j2eo-data` relative to the project's root. Next, it will put the If you no more need that folder, run 
```sh
rm -rf ../j2eo-data
```

---

## Motivation

This project is a part of Polystat project, the goal of which is to statically analyze different languages using EOLANG,
the implementation of phi-calculus. In order to do that, the first step is to convert source language into EO. This
particular repository contains translator from Java to EO.

---

## Decisions

**Q**: Why do we implement yet another Java parser?

**A**: Publicly available parsers only support older versions of Java, while we aim to support the latest version (
currently 16). Thus, we had to create our own parser.

Also in recent versions, external Java grammar implemented in ANTLR was added as an alternative. It claims to support Java 17, and it does, as for our testing on big projects.

<br>

**Q**: Why do we implement EO AST?

**A**: Working with AST instead of raw strings allows utilization of Java compiler's type checking to minimize amount of
bugs in our code. It is also much easier to work with abstraction layer than with strings.

<br>

---

## How does it work?

- First, the Java source code files are parsed recursively.
- Then, for each file, translator converts Java AST to EO AST.
- Then, EO AST is printed out as a source code to output directory in the same directory structure.

---

## Troubleshooting

### Java
Make sure you have these in sync (*mentioning* (not pointing to) the same `jdk` directory)
- `which java`
- `which javac`
  - configure alternatives in case of mismatch ([link](https://stackoverflow.com/a/47432365))
- `echo $JAVA_HOME`
  - See how to set `$JAVA_HOME` ([link](https://stackoverflow.com/a/18972665))
  - If it still points to a wrong directory, see where you might have set it ([link](https://unix.stackexchange.com/a/154957)) and edit that place

---

## Awesome Bugs translation
[Awesome bugs repo](https://github.com/polystat/awesome-bugs)

.java files:
- [directStateAccess](src/test/resources/awesome_bugs/java/directStateAccess)
- [divByZero](src/test/resources/awesome_bugs/java/divByZero)
- [inheritance](src/test/resources/awesome_bugs/java/inheritance)
- [unjustifiedAssumption](src/test/resources/awesome_bugs/java/unjustifiedAssumption)

Translations:
- [directStateAccess](src/test/resources/awesome_bugs/eo/directStateAccess)
- [divByZero](src/test/resources/awesome_bugs/eo/divByZero)
- [inheritance](src/test/resources/awesome_bugs/eo/inheritance)
- [unjustifiedAssumption](src/test/resources/awesome_bugs/eo/unjustifiedAssumption)

---

## Examples of Java to EOLang translation

We use Java language specification document as a foundation for Java feature hierarchy.  
Java 16 language specification: [see .pdf file](https://docs.oracle.com/javase/specs/jls/se16/jls16.pdf)

###[Ch. 4 - Types, Values, and Variables](src/test/resources/test_ready/ch_4_types_values_variables)  
- Increment operator:
	[Java](src/test/resources/test_ready/ch_4_types_values_variables/sec_4_2_primitive_types_and_values/sec_4_2_2_integer_operations/IncrementOperator.java)
	to
	[EO](src/test/resources/translated_tests/ch_4_types_values_variables/sec_4_2_primitive_types_and_values/sec_4_2_2_integer_operations/IncrementOperator.eo)

###[Ch. 5 - Conversions and Contexts](src/test/resources/test_ready/ch_5_conversions_and_contexts)

###[Ch. 6 - Names](src/test/resources/test_ready/ch_6_names)
- A simple declaration:
	[Java](src/test/resources/test_ready/ch_6_names/sec_6_1_declarations/SimpleDeclaration.java)
	to
	[EO](src/test/resources/translated_tests/ch_6_names/sec_6_1_declarations/SimpleDeclaration.eo)

###[Ch. 7 - Packages and Modules WIP](src/test/resources/test_ready/ch_7_packages_and_modules)

###[Ch. 8 - Classes](src/test/resources/test_ready/ch_8_classes)
- Method class member:
	[Java](src/test/resources/test_ready/ch_8_classes/sec_8_2_class_members/MethodClassMember.java)
	to
	[EO](src/test/resources/translated_tests/ch_8_classes/sec_8_2_class_members/MethodClassMember.eo)
- Field initialization:
	[Java](src/test/resources/test_ready/ch_8_classes/sec_8_3_field_declarations/sec_8_3_2_field_initialization/SimpleFieldInit.java)
	to
	[EO](src/test/resources/translated_tests/ch_8_classes/sec_8_3_field_declarations/sec_8_3_2_field_initialization/SimpleFieldInit.eo)
- Method declaration:
	[Java](src/test/resources/test_ready/ch_8_classes/sec_8_4_method_declarations/sec_8_4_1_formal_parametes/SimpleMethod.java)
	to
	[EO](src/test/resources/translated_tests/ch_8_classes/sec_8_4_method_declarations/sec_8_4_1_formal_parametes/SimpleMethod.eo)
- Inner class:
	[Java](src/test/resources/test_ready/ch_8_classes/sec_8_5_member_class_and_interface_declarations/SimpleInnerClass.java)
	to
	[EO](src/test/resources/translated_tests/ch_8_classes/sec_8_5_member_class_and_interface_declarations/SimpleInnerClass.eo)

###[Ch. 9 - Interfaces](src/test/resources/test_ready/ch_9_interfaces)

###[Ch. 10 - Arrays](src/test/resources/test_ready/ch_10_arrays)
- Primitive array declaration:
    [Java](src/test/resources/test_ready/ch_10_arrays/sec_10_2_array_variables/PrimitiveArrayDecl.java)
    to
    [EO](src/test/resources/translated_tests/ch_10_arrays/sec_10_2_array_variables/PrimitiveArrayDecl.eo)

###[Ch. 11 - Exceptions WIP](src/test/resources/test_ready/ch_11_exceptions)

###[Ch. 14 - Block Statements, and Patterns](src/test/resources/test_ready/ch_14_blocks_statements_and_patterns)

###[Ch. 15 - Expressions](src/test/resources/test_ready/ch_15_expressions)
- Left-hand operands are evaluated first: 
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_7_evaluation_order/sec_15_7_1_evaluate_left_hand_operand_first/SimpleLeftHandOperandIsEvaluatedFirst.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_7_evaluation_order/sec_15_7_1_evaluate_left_hand_operand_first/SimpleLeftHandOperandIsEvaluatedFirst.eo)
- Integer literal:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_8_primary_expressions/sec_15_8_1_lexical_literals/IntegerLiteral.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_8_primary_expressions/sec_15_8_1_lexical_literals/IntegerLiteral.eo)
- Complex parenthesized expression:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_8_primary_expressions/sec_15_8_5_parenthesized_expressions/ComplexParenthExpression.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_8_primary_expressions/sec_15_8_5_parenthesized_expressions/ComplexParenthExpression.eo)
- Creation of a simple integer array:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_10_array_creation_and_access_expressions/sec_15_10_1_array_creation_expressions/SimpleIntegerArray.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_10_array_creation_and_access_expressions/sec_15_10_1_array_creation_expressions/SimpleIntegerArray.eo)
- Postfix increment:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_14_postfix_expressions/sec_15_14_2_postfix_increment_operator/SimplePostfixIncrement.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_14_postfix_expressions/sec_15_14_2_postfix_increment_operator/SimplePostfixIncrement.eo)
- Unary plus operator:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_15_unary_operators/sec_15_15_3_unary_plus_operator/SimpleUnaryPlus.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_15_unary_operators/sec_15_15_3_unary_plus_operator/SimpleUnaryPlus.eo)
- Multiplication operator:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_17_multiplicative_operators/sec_15_17_1_multiplication_operator/SimpleMultiplication.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_17_multiplicative_operators/sec_15_17_1_multiplication_operator/SimpleMultiplication.eo)
- Variable right shift:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_19_shift_operators/VariableRightShift.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_19_shift_operators/VariableRightShift.eo)
- Greater operator:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_20_relational_operators/sec_15_20_1_numerical_comparison_operators/SimpleGreaterComparison.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_20_relational_operators/sec_15_20_1_numerical_comparison_operators/SimpleGreaterComparison.eo)
- Assignment operator:
    [Java](src/test/resources/test_ready/ch_15_expressions/sec_15_26_assignment_operators/sec_15_26_1_simple_assignment_operator/SimpleAssignmentOperator.java)
    to
    [EO](src/test/resources/translated_tests/ch_15_expressions/sec_15_26_assignment_operators/sec_15_26_1_simple_assignment_operator/SimpleAssignmentOperator.eo)

###[Ch. 16 - Definite Assignments WIP](src/test/resources/test_ready/ch_16_definite_assignment)

###[Ch. 18 - Type inference WIP](src/test/resources/test_ready/ch_18_type_inference)

