# Checkstyle Test Conventions

**Checkstyle checks for test conventions**

[Checkstyle Test Conventions](https://github.com/AdoraMel/checkstyle-test-conventions/) adds 2 additonal checks for the
[Checkstyle](https://github.com/checkstyle/checkstyle) static code analysis tool.

## Checks

### Annotated Method Names

A check designed to enforce that all `@Test` methods should start with "test".  
Can be configured to check for any pattern and annotations. For example, can also ensure that all `@BeforeEach` methods are named `"setUp()"`.

### Annotated Method Comments

A check designed to enforce that all `@Test` methods have comments in which "given", "when", and "then" appear in order.  
Can also be configured to check for any pattern and annotations.

## Example usage

See the Javadoc in the [implementation](https://github.com/AdoraMel/checkstyle-test-conventions/tree/main/src/main/java/com/adoramel/checks) or the [Examples folder](https://github.com/AdoraMel/checkstyle-test-conventions/tree/main/examples).

## License

Checkstyle Test Conventions licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0).
