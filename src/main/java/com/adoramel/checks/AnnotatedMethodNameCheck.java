package com.adoramel.checks;
import com.puppycrawl.tools.checkstyle.api.*;

import java.util.Arrays;
 
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

  //set annotations
  public void setAnnotations(String[] annotations)
  {
    _annotations = annotations;
  }

  //set method pattern
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