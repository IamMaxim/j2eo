package tree.Declaration;

import tree.*;
import tree.Statement.Block;
import tree.Type.Type;
import tree.Type.TypeList;
import tree.Type.TypeParameters;

// ConstructorDeclaration
//    : /*ModifierSeqOpt*/ ConstructorDeclarator ThrowsOpt ConstructorBody
//    ;
//
//ConstructorDeclarator
//    : TypeParametersOpt IDENTIFIER LPAREN FormalParameterList/*FormalParameters*/ RPAREN
//    ;
//
//ConstructorBody
//    : LBRACE                                                 RBRACE
//    | LBRACE ExplicitConstructorInvocation                   RBRACE
//    | LBRACE                               BlockStatementSeq RBRACE
//    | LBRACE ExplicitConstructorInvocation BlockStatementSeq RBRACE
//    ;
public class ConstructorDeclaration extends MethodDeclaration
{
    // Structure: all from the base class
//  public Modifiers modifiers;
//  public TypeParameters typeParameters;
//  public Type type;                           // always null
//  public String name;                         // always null
//  public ReceiverDeclaration receiver;
//  public ParameterDeclarations parameters;
//  public Dims dims;                           // always null
//  public TypeList exceptions;
//  public Block methodBody;

    // Creation
    public ConstructorDeclaration(Modifiers mods,
                                  TypeParameters typePars,
                                  ParameterDeclarations pars,
                                  TypeList excs,
                                  Block body)
    {
        super(mods,typePars,null,null,pars,null,excs,body);
    }

    // Reporting
    public void report(int sh)
    {
        title("CONSTRUCTOR",sh);
    }

    // Generation
    public void generateEO()
    {

    }

}
