package com.yc.ycutilsx.kotlin

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 *
 */
class KotlinFlow {
    lateinit var flow: Flow<Int>
    fun setupFlow() {
        flow = flow {
            println("Start flow")
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(500)
                println("Emitting $it")
                emit(it)
            }
        }.map {
            it * it
        }.flowOn(Dispatchers.Default)
    }

}

fun main() {

//    CoroutineScope(Dispatchers.IO).launch {
//        val a = KotlinFlow()
//        a.flow1.collect {
//            println("--$it")
//        }
//    }

}