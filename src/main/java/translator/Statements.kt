package translator

import eotree.EOCopy
import eotree.EOExpr
import tree.Statement.BlockStatement
import tree.Statement.IfThenElse
import tree.Statement.Statement
import tree.Statement.StatementExpression

fun mapBlockStatement(stmt: BlockStatement): EOExpr =
    // Block statement is one of three variants
    when {
        stmt.statement != null ->
            mapStatement(stmt.statement)
        stmt.declaration != null ->
            throw UnsupportedOperationException("Not implemented")
        stmt.expression != null ->
            mapExpression(stmt.expression)
        else ->
            throw IllegalArgumentException("BlockStatement does not have any known type")
    }

fun mapStatement(statement: Statement): EOExpr =
    when (statement) {
        is StatementExpression ->
            mapStatementExpression(statement)
        is IfThenElse ->
            mapIfThenElse(statement)
        else ->
            throw IllegalArgumentException("Statement of type ${statement.javaClass.simpleName} is not supported")
    }

fun mapStatementExpression(stmtExpr: StatementExpression): EOExpr {
    return mapExpression(stmtExpr.expression)
}

fun mapIfThenElse(statement: IfThenElse): EOExpr =
    EOCopy(
        "if.",
        listOfNotNull(
            mapExpression(statement.condition),
            mapStatement(statement.thenPart),
            if (statement.elsePart != null) mapStatement(statement.elsePart) else null,
        )
    )