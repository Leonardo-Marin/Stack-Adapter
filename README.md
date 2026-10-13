# Stack Adapter

A `java.util.Stack`-compatible adapter for **Java ME CLDC 1.1**, built on top of the CLDC 1.1 `Vector` class.

## The problem

Code written for **J2SE 1.4.2** that relies on `Stack`, and on the Collections Framework interfaces behind it
(`Collection`, `List`, `Iterator`, `ListIterator`), cannot run as-is on **CLDC 1.1**. CLDC is the Java ME
configuration for constrained devices, and it ships neither those interfaces nor a complete `Stack`.

This project fills that gap with an **adapter** (Adapter design pattern). The `StackAdapter` class exposes the
`Stack` API and the full J2SE 1.4.2 `List` contract. Internally it delegates to the CLDC 1.1 `Vector` (the
*adaptee*). The adapter code (the `myAdapter` package) uses **only** features available in CLDC 1.1: no generics,
no reflection and nothing introduced after Java 1.4.2, so it can be compiled with any Java version. Only the
tests rely on a modern JDK, because they use JUnit 4.

## Features

- **Stack operations**: `push`, `pop`, `peek`, `empty`, `search`, behaving exactly like `java.util.Stack` in J2SE 1.4.2.
- **Complete `List` / `Collection` behaviour**, including every *optional operation*: positional access, bulk
  operations (`addAll`, `removeAll`, `retainAll`, `containsAll`), `equals`/`hashCode` with the standard list semantics, `toArray`.
- **Iterators**: an `HIterator`, plus a bidirectional `HListIterator` with `previous`, `nextIndex`, `previousIndex`,
  `set`, `add` and `remove`. Iterators work directly on the stack, so changes made after their creation are visible through them.
- **`subList` views**: dynamic views backed by the stack. Changes made through a view, including structural ones,
  are reflected on the stack, and views can be nested (`subList` of a `subList`).
- **Null and heterogeneous elements** are supported.

To avoid clashes with the modern `java.util` interfaces, the framework interfaces are redefined in the
`myAdapter` package as `HCollection`, `HList`, `HIterator` and `HListIterator`, with the same methods and
contracts as their J2SE 1.4.2 counterparts. The package also defines `IllegalStateException` and
`UnsupportedOperationException`, because neither exists in CLDC 1.1.

### Design notes

- The element at index `0` is the **bottom** of the stack and the element at index `size() - 1` is the **top**.
  `push` appends to the end of the `Vector`. Iterators go from bottom to top, the same order used by `java.util.Stack`.
- `subList` stores no elements: it maps view index `i` to stack index `offset + i` and delegates every operation to the stack.
- `toArray(Object[])` returns a plain `Object[]` when the given array is too small, because CLDC 1.1 has no
  `java.lang.reflect.Array` to create an array of the same runtime type.

## Project structure

```
myAdapter/   the adapter: StackAdapter + HCollection, HList, HIterator, HListIterator and the exceptions
myTest/      JUnit test suites and the TestRunner
JUnit/       JUnit 4.13.2 and Hamcrest 1.3 jars (only needed for the tests)
docs/        generated Javadoc (open docs/index.html)
```

## Build and run

Run all commands from the root of the repository. Any modern JDK works.

**Compile**

```bash
# Linux / macOS
javac -cp ".:JUnit/junit-4.13.2.jar:JUnit/hamcrest-core-1.3.jar" myAdapter/*.java myTest/*.java
# Windows
javac -cp ".;JUnit/*" myAdapter/*.java myTest/*.java
```

**Run the test suite**

```bash
# Linux / macOS
java -cp ".:JUnit/junit-4.13.2.jar:JUnit/hamcrest-core-1.3.jar" myTest.TestRunner
# Windows
java -cp ".;JUnit/*" myTest.TestRunner
```

`TestRunner` runs every suite and prints the number of tests executed, the number of failures (with details)
and the total execution time.

**Regenerate the documentation**

```bash
javadoc -d docs -cp "JUnit/junit-4.13.2.jar:JUnit/hamcrest-core-1.3.jar" -sourcepath . myAdapter myTest
```

(on Windows, separate the two jars with `;` instead of `:`)

## Tests

The project was developed with **Test Driven Development**: 237 JUnit 4 tests in four suites.

| Suite | What it covers |
|---|---|
| `StackAdapterTest` | stack operations and every `HCollection` / `HList` method |
| `StackIteratorTest` | `HIterator`: forward traversal and removal in different positions |
| `StackListIteratorTest` | `HListIterator`: backward and mixed traversal, indices, `set`, `add`, `remove` |
| `StackSubListTest` | `subList` views: index mapping, bounds, and changes reflected on the stack |

The tests cover standard behaviour, boundary cases (empty stack, first/last index, `index == size()`), `null`
and heterogeneous elements, and every exception required by the J2SE 1.4.2 contracts.

Every suite and every test method is documented in Javadoc with a fixed template:

- **Summary**
- **Test Case Design**
- **Test Description**: the steps and the values used
- **Pre-Condition**
- **Post-Condition**
- **Expected Results**

## Author

Leonardo Marin
