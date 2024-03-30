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
 * Checks that methods with certain annotations have comments that adhere to a
 * specified pattern. The pattern applies to all comments concatenated together.
 * </p>
 * 
 * <p>
 * By default checks that methods annotated with {@code @Test} have comments where
 * "given", "when", "then" appear in order:
 * </p>
 * <pre>
    @Test
    public void additionTest() { //ok
        //given
        MyClass example = new MyClass();
        //when/then
        assertEquals(8, example.add(5, 3));
    }
    <br>
    @Test
    public void testSubtract() { //violation, "when" missing
        //given a new class
        MyClass example = new MyClass();
        //then
        assertEquals(5, example.subtract(8, 3));
    }
 * </pre>
 * <br>
 * <ul>
 * <li>
 * Property {@code annotations} - Sets the annotations that method comments wil
 * be checked for.
 * Type is {@code String[]}.
 * Default value is {@code @Test}.
 * </li>
 * <li>
 * Property {@code commentPattern} - Sets the pattern to match valid comments.<br>
 * <b>NOTE</b>: All comments are concatenated when pattern is checked.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "(?s)^.*\\bgiven\\b.*\\bwhen\\b.*\\bthen\\b.*$"}.
 * </li>
 * </ul>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 */
public class TestMethodCommentsCheck extends AbstractCheck
{
  private static final String[] _DEFAULT_ANNOTATIONS = { "@Test" };
  private String[] _annotations =  _DEFAULT_ANNOTATIONS;

  private static final String _DEFAULT_COMMENTS_PATTERN = "(?s)^.*\\bgiven\\b.*\\bwhen\\b.*\\bthen\\b.*$";
  private String _commentsPattern =  _DEFAULT_COMMENTS_PATTERN;

  private DetailAST _currentMethodDef = null;
  private String _currentMethodComments = "";
 
  @Override
  public boolean isCommentNodesRequired() {
      return true;
  }

  @Override
  public int[] getDefaultTokens()
  {
    return new int[]{TokenTypes.METHOD_DEF, TokenTypes.ANNOTATION, TokenTypes.COMMENT_CONTENT};
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
    switch (ast.getType()) {
      case TokenTypes.ANNOTATION:
        if (_isValidAnnotation(ast)) {
          DetailAST methodDef = _getMethodNode(ast);
          _currentMethodDef = methodDef != null ? methodDef : _currentMethodDef;
        }
        break;
      
      case TokenTypes.COMMENT_CONTENT:
        if (_currentMethodDef != null) {
          _currentMethodComments += ast.getText();
        }
        break;
    
      default:
        break;
    }
  }

  @Override
  public void leaveToken(DetailAST ast)
  {
    if (ast.equals(_currentMethodDef)){
      _checkMethodCommentsPattern(ast);

      _currentMethodDef = null;
      _currentMethodComments = "";
    }
  }

  /**
     * Setter.
     *
     * @param annotations Sets the annotations that method comments wil
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
     * @param commentsPattern  Sets the pattern to match valid comments.<br>
     * <br><b>NOTE</b>: All comments are concatenated when pattern is checked. <br>
     * <br>Default value is {@code "(?s)^.*\\bgiven\\b.*\\bwhen\\b.*\\bthen\\b.*$"}.
     */
  public void setCommentsPattern(String commentsPattern)
  {
    _commentsPattern = commentsPattern;
  }
 
  private boolean _isValidAnnotation(DetailAST ast)
  {
    // find the IDENT node below the ANNOTATION
    DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
    // get the annotation text
    String annotationText = "@" + ident.getText();
     
    return Arrays.asList(_annotations).contains(annotationText);
  }

  private DetailAST _getMethodNode(DetailAST ast)
  {
    // find the METHOD_DEF node parent: ANNOTATION <- MODIFIERS <- METHOD_DEF
    DetailAST methodDef = ast.getParent().getParent();
    // verify METHOD_DEF node
    if (methodDef.getType() != TokenTypes.METHOD_DEF) {
      return null;
    }
    
    return methodDef;
  }
 
  private void _checkMethodCommentsPattern(DetailAST methodDef)
  {
    // check regex
    if (!_currentMethodComments.matches(_commentsPattern))
    {
      // report violation if invalid comments pattern
      String methodName = methodDef.findFirstToken(TokenTypes.IDENT).getText();
      String message = String.format(
        "Comments of test method \"%s\" do not match pattern \"%s\"", methodName, _commentsPattern);
      log(methodDef.getLineNo(), message);
    }
  }
}