package com.example.monitoringgizi.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.FileProvider
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.example.monitoringgizi.data.model.*
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.layout.borders.Border
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class PDFGenerator {
    companion object {
        private fun openPDF(context: Context, file: File) {
            try {
                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    file
                )
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(uri, "application/pdf")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                context.startActivity(Intent.createChooser(intent, "Buka PDF dengan"))
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "Tidak dapat membuka PDF: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun generateLaporanHarianIbuHamil(
            context: Context,
            ibuHamil: IbuHamil,
            pencatatanList: List<PencatatanHarianIbuHamil>
        ): File {
            val fileName = "Laporan_Harian_${ibuHamil.nama}_${System.currentTimeMillis()}.pdf"
            val file = File(context.filesDir, fileName)

            val writer = PdfWriter(file)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc, PageSize.A4)
            document.setMargins(40f, 40f, 40f, 40f)

            // Header
            document.add(
                Paragraph("KARTU KONTROL KONSUMSI MT IBU HAMIL DAN PENCATATAN KONDISI KESEHATAN HARIAN IBU HAMIL")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(14f)
            )

            document.add(Paragraph("\n"))

            // Info Ibu Hamil
            document.add(createKeyValueRow("Nama Ibu", ": ${ibuHamil.nama}")
                .setBold()
            )
            document.add(createKeyValueRow("Usia Ibu/Usia Kehamilan", ": ${ibuHamil.usia} tahun/${ibuHamil.usiaKehamilan} bulan")
                .setBold()
            )
            document.add(createKeyValueRow("Alamat", ": ${ibuHamil.alamat}")
                .setBold()
            )

            document.add(Paragraph("\n"))

            // Tabel Utama
            val table = Table(UnitValue.createPercentArray(floatArrayOf(15f, 20f, 25f, 40f)))
                .useAllAvailableWidth()

            // Header Tabel
            val headerStyle = TextAlignment.CENTER
            table.addCell(createHeaderCell("Hari, Tanggal", headerStyle))
            table.addCell(createHeaderCell("Pemberian MT Hari ke-", headerStyle))
            table.addCell(createHeaderCell("Keterangan Pemberian MT", headerStyle))
            table.addCell(createHeaderCell("Kondisi Kesehatan", headerStyle))

            // Isi Tabel
            val sortedPencatatan = pencatatanList.sortedBy { it.tanggal }
            for (i in 1..30) {
                val pencatatan = sortedPencatatan.getOrNull(i - 1)

                table.addCell(createContentCell(pencatatan?.tanggal ?: "$i"))
                table.addCell(createContentCell(pencatatan?.pemberianKe?.toString() ?: ""))
                table.addCell(createContentCell(if (pencatatan?.statusMakanan == true) "Habis" else if (pencatatan != null) "Tidak Habis" else ""))
                table.addCell(createContentCell(
                    when {
                        pencatatan == null -> ""
                        pencatatan.statusKesehatan -> "Sehat"
                        else -> "Sakit (${pencatatan.keteranganSakit ?: "-"})"
                    }
                ))
            }

            document.add(table)
            document.close()
            return file
        }

        fun generateLaporanBulananIbuHamil(
            context: Context,
            ibuHamil: IbuHamil,
            pemantauanList: List<PemantauanBulananIbuHamil>
        ): File {
            val fileName = "Laporan_Bulanan_${ibuHamil.nama}_${System.currentTimeMillis()}.pdf"
            val file = File(context.filesDir, fileName)

            val writer = PdfWriter(file)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc, PageSize.A4)
            document.setMargins(40f, 40f, 40f, 40f)

            // Header
            document.add(
                Paragraph("PEMANTAUAN BERAT BADAN IBU HAMIL DAN USIA KEHAMILANNYA")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(14f)
            )

            document.add(Paragraph("\n"))

            // Info Detail Utama
            document.add(createKeyValueRow("Nama Ibu hamil", ": ${ibuHamil.nama}")
                .setBold()
            )
            document.add(createKeyValueRow("Tanggal Bulan Tahun lahir", ": ${ibuHamil.tanggalLahir}")
                .setBold()
            )
            document.add(createKeyValueRow("Alamat", ": ${ibuHamil.alamat}")
                .setBold()
            )
            document.add(Paragraph("\n"))

            // Info Pengukuran
            document.add(createKeyValueRow("Usia Kehamilan", ": ${ibuHamil.usiaKehamilan} bulan")
                .setBold()
            )
            document.add(createKeyValueRow("Berat Badan Awal", ": ${ibuHamil.beratBadanAwal} kg")
                .setBold()
            )
            document.add(createKeyValueRow("Tinggi Badan", ": ${ibuHamil.tinggiBadanAwal} cm")
                .setBold()
            )
            document.add(createKeyValueRow("IMT Pra Hamil/Trimester 1", ": ${ibuHamil.imtPraHamil} kg/m²")
                .setBold()
            )
            document.add(createKeyValueRow("LLA", ": ${ibuHamil.lingkarLuarAtas} cm")
                .setBold()
            )
            document.add(createKeyValueRow("Kadar Hb", ": ${ibuHamil.kadarHemoglobin} g/dl")
                .setBold()
            )

            document.add(Paragraph("\n"))

            // Tabel Pemantauan
            val table = Table(UnitValue.createPercentArray(floatArrayOf(10f, 15f, 15f, 20f, 20f, 20f)))
                .useAllAvailableWidth()

            // Header Tabel
            table.addCell(createHeaderCell("No", TextAlignment.CENTER))
            table.addCell(createHeaderCell("Hari, Tanggal", TextAlignment.CENTER))
            table.addCell(createHeaderCell("Pemantauan Bulan ke-", TextAlignment.CENTER))
            table.addCell(createHeaderCell("BB Kader", TextAlignment.CENTER))
            table.addCell(createHeaderCell("BB Nakes", TextAlignment.CENTER))
            table.addCell(createHeaderCell("Lingkar lengan atas (LLA)", TextAlignment.CENTER))

            // Isi Tabel
            pemantauanList.forEachIndexed { index, pemantauan ->
                table.addCell(createContentCell((index + 1).toString()))
                table.addCell(createContentCell(pemantauan.tanggal))
                table.addCell(createContentCell(pemantauan.pemantauanKe.toString()))
                table.addCell(createContentCell("${pemantauan.beratBadanKader} kg"))
                table.addCell(createContentCell(pemantauan.beratBadanNakes?.toString()?.plus(" kg") ?: "-"))
                table.addCell(createContentCell("${pemantauan.lingkarLuarAtas} cm"))
            }

            document.add(table)
            document.close()
            return file
        }

        fun generateLaporanHarianBalita(
            context: Context,
            balita: Balita,
            pencatatanList: List<PencatatanHarianBalita>
        ): File {
            println("Debug PDF - Mulai generate PDF harian")
            println("Debug PDF - Data Balita: ${balita.namaBalita}")
            println("Debug PDF - Jumlah Pencatatan: ${pencatatanList.size}")

            val fileName = "Laporan_Harian_${balita.namaBalita}_${System.currentTimeMillis()}.pdf"
            val file = File(context.filesDir, fileName)

            try {
                val writer = PdfWriter(file)
                val pdfDoc = PdfDocument(writer)
                val document = Document(pdfDoc, PageSize.A4)

                // Header
                document.add(
                    Paragraph("KARTU KONTROL KONSUMSI MT BALITA DAN PENCATATAN KONDISI KESEHATAN HARIAN BALITA")
                        .setTextAlignment(TextAlignment.CENTER)
                        .setBold()
                )

                // Info Balita
                document.add(
                    Table(2).apply {
                        addCell(Cell().add(Paragraph("Nama Balita")).setBorder(Border.NO_BORDER))
                        addCell(Cell().add(Paragraph(": ${balita.namaBalita}")).setBorder(Border.NO_BORDER))
                        addCell(Cell().add(Paragraph("Nama Ibu")).setBorder(Border.NO_BORDER))
                        addCell(Cell().add(Paragraph(": ${balita.namaIbu}")).setBorder(Border.NO_BORDER))
                        addCell(Cell().add(Paragraph("Tanggal Lahir")).setBorder(Border.NO_BORDER))
                        addCell(Cell().add(Paragraph(": ${balita.tanggalLahir}")).setBorder(Border.NO_BORDER))
                        addCell(Cell().add(Paragraph("Alamat")).setBorder(Border.NO_BORDER))
                        addCell(Cell().add(Paragraph(": ${balita.alamat}")).setBorder(Border.NO_BORDER))
                    }
                )

                // Tabel pencatatan
                val table = Table(UnitValue.createPercentArray(floatArrayOf(15f, 20f, 25f, 40f)))
                    .useAllAvailableWidth()

                // Header tabel
                arrayOf("Tanggal", "Pemberian Ke-", "Status Makanan", "Kondisi Kesehatan").forEach {
                    table.addCell(
                        Cell()
                            .add(Paragraph(it))
                            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                            .setTextAlignment(TextAlignment.CENTER)
                    )
                }

                // Isi tabel
                if (pencatatanList.isEmpty()) {
                    table.addCell(Cell(1, 4).add(Paragraph("Tidak ada data")).setTextAlignment(TextAlignment.CENTER))
                } else {
                    pencatatanList.forEach { pencatatan ->
                        table.addCell(Cell().add(Paragraph(pencatatan.tanggal)).setTextAlignment(TextAlignment.CENTER))
                        table.addCell(Cell().add(Paragraph(pencatatan.pemberianKe.toString())).setTextAlignment(TextAlignment.CENTER))
                        table.addCell(Cell().add(Paragraph(if (pencatatan.habis) "Habis" else "Tidak Habis")).setTextAlignment(TextAlignment.CENTER))
                        table.addCell(Cell().add(Paragraph(
                            if (pencatatan.sehat) "Sehat"
                            else "Sakit (${pencatatan.keteranganSakit ?: "-"})"
                        )).setTextAlignment(TextAlignment.CENTER))
                    }
                }

                document.add(table)
                document.close()

                println("Debug PDF - PDF berhasil dibuat: ${file.absolutePath}")
                return file

            } catch (e: Exception) {
                println("Debug PDF - Error saat generate PDF: ${e.message}")
                e.printStackTrace()
                throw e
            }
        }

        fun generateLaporanBulananBalita(
            context: Context,
            balita: Balita,
            pemantauanList: List<PemantauanBulananBalita>
        ): File {
            val fileName = "Laporan_Bulanan_${balita.namaBalita}_${System.currentTimeMillis()}.pdf"
            val file = File(context.filesDir, fileName)

            val writer = PdfWriter(file)
            val pdfDoc = PdfDocument(writer)
            val document = Document(pdfDoc, PageSize.A4)
            document.setMargins(40f, 40f, 40f, 40f)

            // Header
            document.add(
                Paragraph("PEMANTAUAN BERAT BADAN DAN PANJANG BADAN/TINGGI BADAN BALITA")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold()
                    .setFontSize(14f)
            )

            document.add(Paragraph("\n"))

            // Info Balita
            document.add(createKeyValueRow("Nama Balita", ": ${balita.namaBalita}"))
            document.add(createKeyValueRow("Nama Ibu", ": ${balita.namaIbu}"))
            document.add(createKeyValueRow("Tanggal Lahir", ": ${balita.tanggalLahir}"))
            document.add(createKeyValueRow("Berat Badan Awal", ": ${balita.beratBadanAwal} kg"))
            document.add(createKeyValueRow("Panjang/Tinggi Badan Awal", ": ${balita.tinggiBadanAwal} cm"))

            document.add(Paragraph("\n"))

            // Tabel
            val table = Table(UnitValue.createPercentArray(floatArrayOf(8f, 12f, 16f, 16f, 16f, 16f, 16f)))
                .useAllAvailableWidth()

            // Header Tabel
            table.addCell(createHeaderCell("No", TextAlignment.CENTER))
            table.addCell(createHeaderCell("Tanggal", TextAlignment.CENTER))
            table.addCell(createHeaderCell("BB Kader", TextAlignment.CENTER))
            table.addCell(createHeaderCell("BB Nakes", TextAlignment.CENTER))
            table.addCell(createHeaderCell("Status", TextAlignment.CENTER))
            table.addCell(createHeaderCell("PB/TB Kader", TextAlignment.CENTER))
            table.addCell(createHeaderCell("PB/TB Nakes", TextAlignment.CENTER))

            // Isi Tabel
            pemantauanList.forEachIndexed { index, pemantauan ->
                table.addCell(createContentCell((index + 1).toString()))
                table.addCell(createContentCell(pemantauan.tanggal))
                table.addCell(createContentCell("${pemantauan.beratBadanKader} kg"))
                table.addCell(createContentCell("${pemantauan.beratBadanNakes} kg"))
                table.addCell(createContentCell(pemantauan.statusBeratBadan))
                table.addCell(createContentCell("${pemantauan.panjangBadanKader} cm"))
                table.addCell(createContentCell("${pemantauan.panjangBadanNakes} cm"))
            }

            document.add(table)
            document.close()

            openPDF(context, file)
            return file
        }

        private fun createHeaderCell(text: String, alignment: TextAlignment = TextAlignment.LEFT): Cell {
            return Cell()
                .add(Paragraph(text))
                .setTextAlignment(alignment)
                .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                .setBold()
                .setPadding(5f)
        }

        private fun createContentCell(text: String, alignment: TextAlignment = TextAlignment.CENTER): Cell {
            return Cell()
                .add(Paragraph(text))
                .setTextAlignment(alignment)
                .setPadding(5f)
        }

        private fun createKeyValueRow(key: String, value: String): Paragraph {
            return Paragraph("$key$value")
                .setFontSize(11f)
                .setMultipliedLeading(1.2f)
        }
    }
}