sealed class ExpressionNode {
    data class OperatorNode(
        val operator: Char,
        val left: ExpressionNode,
        val right: ExpressionNode
    ) : ExpressionNode()

    data class NumberNode(
        val value: Double
    ) : ExpressionNode()
}
fun buildExpressionTree(expression: String): ExpressionNode {
    val expr = expression.trim()

    if (expr.startsWith('(') && expr.endsWith(')')) {
        var balance = 0
        var isOuterParentheses = true
        for (i in 0 until expr.length - 1) {
            if (expr[i] == '(') balance++
            if (expr[i] == ')') balance--
            if (balance == 0) {
                isOuterParentheses = false
                break
            }
        }
        if (isOuterParentheses) {
            return buildExpressionTree(expr.substring(1, expr.length - 1))
        }
    }

    val operatorsByPrecedence = listOf(listOf('+', '-'), listOf('*', '/'))

    for (operators in operatorsByPrecedence) {
        var balance = 0
        for (i in expr.length - 1 downTo 0) {
            val char = expr[i]
            if (char == ')') balance++
            if (char == '(') balance--

            if (balance == 0 && char in operators) {
                val leftPart = expr.substring(0, i)
                val rightPart = expr.substring(i + 1)

                return ExpressionNode.OperatorNode(
                    operator = char,
                    left = buildExpressionTree(leftPart),
                    right = buildExpressionTree(rightPart)
                )
            }
        }
    }

    return ExpressionNode.NumberNode(expr.toDouble())
}
