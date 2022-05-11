package ir.part.sdk.ara.data.barjavand.entities

import ir.part.sdk.ara.domain.document.entities.DocumentsStatus


enum class DocumentsStatusEntity(val value: String) {

    Code_11("11"), //در انتظار تایید راهنمای کانون
    Code_11_3("11.3"), //در انتظار اصلاحات توسط متقاضی
    Code_11_6("11.6"), //در انتظار تایید اصلاحات
    Code_12("12"), //در انتظار بررسی مشخصات فردی
    Code_12_3("12.3"), //در انتظار اصلاحات توسط متقاضی
    Code_12_6("12.6"), //در انتظار تایید اصلاحات
    Code_13("13"), //در انتظار دریافت استعلام
    Code_14("14"), //در انتظار تصویب اعتبار سنجی پرونده
    Code_18("18"), //در انتظار پرداخت
    Code_21("21"), //در انتظار دریافت بهبود دهنده اعتباری‌(چک و سفته)
    Code_31("31"), //پرونده اعتبارسنجی تکمیل شد
    Code_100("100"), //رد درخواست توسط راهنمای کانون
    Code_200("200"), //رد درخواست توسط کارشناس اعتبارسنجی
    Code_300("300"), //حذف درخواست توسط متقاضی
    Code_400("400"); //پرونده منقضی شده است


    companion object {

        fun enumValueOf(type: String): DocumentsStatusEntity? {
            enumValues<DocumentsStatusEntity>().forEach {
                if (it.value == type)
                    return it
            }
            return null
        }

    }

    fun toFilesStatus(): DocumentsStatus {
        return when (this) {
            Code_11 -> DocumentsStatus.Code_11
            Code_11_3 -> DocumentsStatus.Code_11_3
            Code_11_6 -> DocumentsStatus.Code_11_6
            Code_12 -> DocumentsStatus.Code_12
            Code_12_3 -> DocumentsStatus.Code_12_3
            Code_12_6 -> DocumentsStatus.Code_12_6
            Code_13 -> DocumentsStatus.Code_13
            Code_14 -> DocumentsStatus.Code_14
            Code_18 -> DocumentsStatus.Code_18
            Code_21 -> DocumentsStatus.Code_21
            Code_31 -> DocumentsStatus.Code_31
            Code_100 -> DocumentsStatus.Code_100
            Code_200 -> DocumentsStatus.Code_200
            Code_300 -> DocumentsStatus.Code_300
            Code_400 -> DocumentsStatus.Code_400
        }
    }
}