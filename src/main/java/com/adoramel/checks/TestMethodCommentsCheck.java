package com.adoramel.checks;
import com.puppycrawl.tools.checkstyle.api.*;

import java.util.Arrays;
 
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

  //set annotations
  public void setAnnotations(String[] annotations)
  {
    _annotations = annotations;
  }

  //set comments pattern
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