package ir.part.sdk.ara.data.barjavand.entities

import ir.part.sdk.ara.domain.document.entities.DocumentsStatus


enum class DocumentsStatusEntity(val value: String) {

    CODE_11("11"), //در انتظار تایید راهنمای کانون
    CODE_11_3("11.3"), //در انتظار اصلاحات توسط متقاضی
    CODE_11_6("11.6"), //در انتظار تایید اصلاحات
    CODE_12("12"), //در انتظار بررسی مشخصات فردی
    CODE_12_3("12.3"), //در انتظار اصلاحات توسط متقاضی
    CODE_12_6("12.6"), //در انتظار تایید اصلاحات
    CODE_13("13"), //در انتظار دریافت استعلام
    CODE_14("14"), //در انتظار تصویب اعتبار سنجی پرونده
    CODE_18("18"), //در انتظار پرداخت
    CODE_21("21"), //در انتظار دریافت بهبود دهنده اعتباری‌(چک و سفته)
    CODE_31("31"), //پرونده اعتبارسنجی تکمیل شد
    CODE_100("100"), //رد درخواست توسط راهنمای کانون
    CODE_200("200"), //رد درخواست توسط کارشناس اعتبارسنجی
    CODE_300("300"), //حذف درخواست توسط متقاضی
    CODE_400("inquiries@rejected"); //پرونده منقضی شده است


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
            CODE_11 -> DocumentsStatus.CODE_11
            CODE_11_3 -> DocumentsStatus.CODE_11_3
            CODE_11_6 -> DocumentsStatus.CODE_11_6
            CODE_12 -> DocumentsStatus.CODE_12
            CODE_12_3 -> DocumentsStatus.CODE_12_3
            CODE_12_6 -> DocumentsStatus.CODE_12_6
            CODE_13 -> DocumentsStatus.CODE_13
            CODE_14 -> DocumentsStatus.CODE_14
            CODE_18 -> DocumentsStatus.CODE_18
            CODE_21 -> DocumentsStatus.CODE_21
            CODE_31 -> DocumentsStatus.CODE_31
            CODE_100 -> DocumentsStatus.CODE_100
            CODE_200 -> DocumentsStatus.CODE_200
            CODE_300 -> DocumentsStatus.CODE_300
            CODE_400 -> DocumentsStatus.CODE_400
        }
    }
}