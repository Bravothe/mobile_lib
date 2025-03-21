package com.example.mathoperation.dialogs

import android.content.Context
import com.example.mathoperation.LoadingDialog

object Dialogs {
    fun showLoadingDialog(context: Context, onFinish: () -> Unit) {
        LoadingDialog.showLoadingDialog(context, onFinish)
    }

    fun showSummaryDialog(
        context: Context, businessName: String, userName: String, itemsPurchased: String,
        currency: String, totalAmount: Double, onNext: (Double) -> Unit
    ) {
        SummaryDialog.showSummaryDialog(context, businessName, userName, itemsPurchased, currency, totalAmount, onNext)
    }

    fun showPaymentStatus(context: Context, isSuccess: Boolean) {
        PaymentStatusDialog.showPaymentStatus(context, isSuccess, 0)
    }

    fun showInsufficientBalance(context: Context) {
        InsufficientBalanceDialog.showInsufficientBalance(context)
    }
}