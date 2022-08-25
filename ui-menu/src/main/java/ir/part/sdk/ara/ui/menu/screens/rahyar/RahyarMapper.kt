package ir.part.sdk.ara.ui.menu.screens.rahyar

import ir.part.sdk.ara.domain.menu.entities.Rahyar

fun Rahyar.toRahyarView() = RahyarView(
    address = address,
    name = name,
    province = province,
    record = record,
    telephone = telephone
)