package com.example.firstapp

import android.util.Log
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.example.firstapp.pagos.PagoViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class ModuloImpresora {

    fun imprimirTicket(pagoInfo: PagoViewModel) {
        var connection = BluetoothPrintersConnections.selectFirstPaired()

        if (connection != null)
        {
            var locale: Locale = Locale("id", "MX")
            var df = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", locale)
            var nf = NumberFormat.getCurrencyInstance(locale)


            var impresora = EscPosPrinter(connection,203, 30f, 30);
            var textoImpresora : String = """
                [C] <b>BELLELY CREDITOS</b>
                [L]
                [L] ${pagoInfo.nombre.toString()} ${System.lineSeparator()}
                [L]${pagoInfo.fechaPago}
                [C]<b>===== RECIBO DE PAGO ======<b> ${System.lineSeparator()}
                [C]================================
                [L]
                [L]1 pago[R]${nf.format(pagoInfo.monto)}
                [C]<b>del credito</b>
                [C]--------------------------------
                [L]SUBTOTAL[R]${nf.format(pagoInfo.monto)} ${System.lineSeparator()}
                [L]MULTA [R]${nf.format(pagoInfo.faltaDePago)}  ${System.lineSeparator()}              
                [L]<b>TOTAL[R]${nf.format((pagoInfo.monto + pagoInfo.faltaDePago))}</b>
             
                """.trimIndent()

            impresora.printFormattedText(textoImpresora, 8f)

        }

        else
        {
            Log.d("IMPRESORA", "NO ESTA CONECTADA")
        }

    }
}