package com.tec.dailylogv2.dl

import java.time.LocalDate
import java.util.*

data class Diagnostico(
    var tipo_de_diagnostico : String ?= null,
    var problema : String ?= null,
    var solucion : String ?= null,
//    var fecha : LocalDate
)
