package com.adoramel.checks;
/*
 * Copyright (c) 2024-2024, the Checkstyle Test Conventions cotributors

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import com.puppycrawl.tools.checkstyle.api.*;

import java.util.Arrays;
 
/**
 * <p>
 * Checks that methods with certain annotations are named to conform to a 
 * specified pattern.
 * </p>
 * 
 * <p>
 * By default checks that methods annotated with {@code @Test} start with "test":
 * </p>
 * <pre>
    @Test
    public void additionTest() { //violation
        MyClass example = new MyClass();
        assertEquals(8, example.add(5, 3));
    }
    <br>
    @Test
    public void testSubtract() { //ok, starts with "test"
        MyClass example = new MyClass();
        assertEquals(5, example.subtract(8, 3));
    }
 * </pre>
 * <br>
 * <ul>
 * <li>
 * Property {@code annotations} - Sets the annotations that method names wil
 * be checked for.
 * Type is {@code String[]}.
     * Default value is {@code ["@Test"]}.
 * </li>
 * <li>
 * Property {@code methodPattern} - Sets the pattern to match valid method names.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^test.*$"}.
 * </li>
 * </ul>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 */
public class AnnotatedMethodNameCheck extends AbstractCheck
{
  private static final String[] _DEFAULT_ANNOTATIONS = { "@Test" };
  private String[] _annotations =  _DEFAULT_ANNOTATIONS;
 
  private static final String _DEFAULT_METHOD_PATTERN = "^test.*$";
  private String _methodPattern = _DEFAULT_METHOD_PATTERN;
 
  @Override
  public int[] getDefaultTokens()
  {
    return new int[]{TokenTypes.ANNOTATION};
  }

  @Override
  public int[] getAcceptableTokens() {
    return getDefaultTokens();
  }

  @Override
  public int[] getRequiredTokens() {
    return getDefaultTokens();
  }
 
  @Override
  public void visitToken(DetailAST ast)
  {
    if (_isValidAnnotation(ast))
    {
        _checkMethodName(ast);
    }
  }

  /**
     * Setter.
     *
     * @param annotations Sets the annotations that method names wil
     * be checked for.<br>
     * Default value is {@code ["@Test"]}.
     */
  public void setAnnotations(String[] annotations)
  {
    _annotations = annotations;
  }

  /**
     * Setter.
     *
     * @param methodPattern Sets the pattern to match valid method names.<br>
     * <br>Default value is {@code "^test.*$"}.
     */
  public void setMethodPattern(String methodPattern)
  {
    _methodPattern = methodPattern;
  }
 
  private boolean _isValidAnnotation(DetailAST ast)
  {
    // find the IDENT node below the ANNOTATION
    DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
    // get the annotation text
    String annotationText = "@" + ident.getText();
     
    return Arrays.asList(_annotations).contains(annotationText);
  }
 
  private void _checkMethodName(DetailAST ast)
  {
    // find the METHOD_DEF node parent: ANNOTATION <- MODIFIERS <- METHOD_DEF
    DetailAST methodDef = ast.getParent().getParent();
    // verify METHOD_DEF node
    if (methodDef.getType() != TokenTypes.METHOD_DEF) {
      return;
    }

    // find the IDENT node below the METHOD_DEF
    DetailAST ident = methodDef.findFirstToken(TokenTypes.IDENT);
    // get name of method
    String methodName = ident.getText();
    // check regex
    if (!methodName.matches(_methodPattern))
    {
      // report violation if invalid method name
      String message = String.format(
        "Method name \"%s\" does not match pattern \"%s\"", methodName, _methodPattern);
      log(ident.getLineNo(), message);
    }
  }
}