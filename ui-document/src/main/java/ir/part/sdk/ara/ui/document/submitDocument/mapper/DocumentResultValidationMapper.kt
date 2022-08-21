package ir.part.sdk.ara.ui.document.submitDocument.mapper

import ir.part.sdk.ara.common.ui.view.DateUtil
import ir.part.sdk.ara.domain.document.entities.DocumentResultValidation
import ir.part.sdk.ara.ui.document.submitDocument.model.DocumentResultValidationView

fun DocumentResultValidation.toDocumentResultValidationView(dateUtil: DateUtil) =
    DocumentResultValidationView(
        proportionate = proportionate?.let {
            (it.toLong() * 1000000)
        } ?: 0,
        perfectProportionate = perfectProportionate?.let {
            (it.toLong() * 1000000)
        } ?: 0,
        disproportionate = disproportionate?.let {
            (it.toLong() * 1000000)
        } ?: 0,
        expireDate = expireDate,
        expireDateView = expireDate?.let
        {
            if (it.length >= 8)
                dateUtil.toDateView(expireDate)
            else
                null
        } ?: "-",
        deptCeiling = deptCeiling?.let {
            (it.toLong() * 1000000)
        } ?: 0,
        commitmentCeiling = roundedCommitmentCeiling(commitmentCeiling),
        commitmentCeilingRemain = roundedCommitmentCeilingRemain(
            commitmentCeiling,
            commitmentPrice
        ),
        commitmentPrice = commitmentPrice,
        chartData = chartData
    )

fun roundedCommitmentCeilingRemain(commitmentCeiling: Int?, commitmentPrice: Long?): Long {
    val commitmentCeilingRemain = if (commitmentCeiling != null && commitmentPrice != null) {
        (commitmentCeiling.toLong() * 1000000) - commitmentPrice
    } else 0
    var rounded = commitmentCeilingRemain

    if (commitmentCeilingRemain > 9999999L) {
        if (commitmentCeilingRemain % 1000000 != 0L) {
            rounded = (commitmentCeilingRemain / 1000000) + 1
            for (i in 0 until commitmentCeilingRemain.toString().length - rounded.toString().length) {
                rounded *= 10
            }
        }
    }
    return rounded
}

fun roundedCommitmentCeiling(commitmentCeiling: Int?): Long {
    val commitmentCeilingRemain = if (commitmentCeiling != null) {
        (commitmentCeiling.toLong() * 1000000)
    } else 0
    var rounded = commitmentCeilingRemain

    if (commitmentCeilingRemain > 9999999L) {
        if ((commitmentCeilingRemain % 1000000) != 0L) {
            rounded = (commitmentCeilingRemain / 1000000) + 1
            for (i in 0 until commitmentCeilingRemain.toString().length - rounded.toString().length) {
                rounded *= 10
            }
        }
    }
    return rounded
}

