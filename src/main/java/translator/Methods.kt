package translator

import arrow.core.None
import arrow.core.firstOrNone
import arrow.core.some
import eotree.*
import tree.Declaration.Declaration
import tree.Declaration.MethodDeclaration
import tree.Declaration.ParameterDeclaration
import tree.Declaration.VariableDeclaration
import tree.Statement.BlockStatement
import tree.Statement.BlockStatements
import util.ListUtils
import java.util.*
import java.util.stream.Collectors

//fun MethodDeclaration.getLocalVariables(): List<Declaration> =
//    // TODO: add support for nested block variables as well
//    methodBody.block.blockStatements
//        .filter { it.declaration != null }
//        .map {  }


fun mapMethodDeclaration(dec: MethodDeclaration): EOBndExpr {
    val obj = EOObject(
        // Non-vararg parameters
        if (dec.parameters != null) // Exclude 'String[] args' fon now
            listOf("this") +
            dec.parameters.parameters.stream()
                .filter { param: ParameterDeclaration -> !param.signEllipsis }
                .map { param: ParameterDeclaration -> param.name }
                .collect(Collectors.toList())
        else
            listOf("this"),

        // Optional vararg parameter
        if (dec.parameters != null)
            dec.parameters.parameters
                .filter { param: ParameterDeclaration -> param.signEllipsis }
                .map { param: ParameterDeclaration -> param.name }
                .also { assert(it.size <= 1) }
                .firstOrNone()
        else
            None,

        // Bound attributes
        // TODO: implement
        dec.methodBody.block.findAllLocalVariables().map { mapVariableDeclaration(it) } +
                listOf(
                    EOBndExpr(
                        EOCopy(
                            "seq",
                            dec.methodBody.block.blockStatements
                                .mapNotNull {
                                    if (it.statement != null)
                                        mapStatement(it.statement)
                                    else if (it.expression != null)
                                        mapExpression(it.expression)
                                    else
                                        null
                                }
                        ),
                        "@"
                    )
                )
    )

    // Contract to check parameter count
    if (dec.parameters != null)
        assert(dec.parameters.parameters.size + 1 ==
                obj.freeAttrs.size + if (obj.varargAttr.nonEmpty()) 1 else 0) {
            "Parameters count of Java method and EO method do not match: ${dec.parameters.parameters.size} vs ${obj.freeAttrs.size + if (obj.varargAttr.nonEmpty()) 1 else 0}"
        }

    return EOBndExpr(
        obj,
        dec.name
    )
}

fun BlockStatements.findAllLocalVariables(): List<VariableDeclaration> =
    blockStatements
        .filter { it.declaration is VariableDeclaration }
        .map { it.declaration as VariableDeclaration }