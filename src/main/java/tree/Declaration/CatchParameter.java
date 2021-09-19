package tree.Declaration;

import lexer.*;
import tree.*;
import tree.Type.TypeList;

// CatchFormalParameter
//    : ModifierSeqOpt CatchType IDENTIFIER DimsOpt
//    ;
//
//CatchType
//    :                    Type
//    | CatchType VERTICAL Type
//    ;
public class CatchParameter extends ParameterDeclaration
{
    // Structure

    // From the base class:
//  public Modifiers modifiers;
//  public Type type;                   -- not used here
//  public String name;
//  public Annotations ellAnnotations;  -- not used here
//  public boolean signEllipsis;        -- not used here
//  public Dims dims;
    // Own attributes:
    public TypeList catchTypes;

    // Creation
    public CatchParameter(Modifiers mods,TypeList types,Token id,Dims dims)
    {
        super(mods,null,id.image,null,false,dims);
        this.catchTypes = types;
    }

    // Reporting
    public void report(int sh)
    {

    }

    // Generation
    public void generateEO()
    {

    }
}
