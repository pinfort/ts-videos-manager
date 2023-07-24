package me.pinfort.tsvideosmanager.console.component

import org.springframework.stereotype.Component

@Component
class UserQuestionComponent {
    private val yesResponses = listOf(
        "y",
        "Y",
        "yes",
        "Yes"
    )
    private val noResponses = listOf(
        "n",
        "N",
        "no",
        "No"
    )

    fun askDefaultFalse(question: String): Boolean {
        println(question)
        println("y/N")
        val answer = readlnOrNull()
        return !answer.isNullOrEmpty() && yesResponses.contains(answer)
    }

    fun askDefaultTrue(question: String): Boolean {
        println(question)
        println("Y/n")
        val answer = readlnOrNull()
        return answer.isNullOrEmpty() || !noResponses.contains(answer)
    }
}
