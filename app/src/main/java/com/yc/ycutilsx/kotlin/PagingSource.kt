//package com.yc.ycutilsx.kotlin
//
///*
// * Copyright 2019 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//import androidx.annotation.IntRange
//import androidx.annotation.RestrictTo
//import androidx.paging.LoadType.REFRESH
//import java.util.concurrent.CopyOnWriteArrayList
//import java.util.concurrent.atomic.AtomicBoolean
//
///**
// * @suppress
// */
//@Suppress("DEPRECATION")
//@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
//fun <Key : Any> PagedList.Config.toRefreshLoadParams(key: Key?): PagingSource.LoadParams<Key>
//= PagingSource.LoadParams.Refresh(key, initialLoadSizeHint, enablePlaceholders)
//abstract class PagingSource<Key : Any, Value : Any> {
//    sealed class LoadParams<Key : Any> constructor(val loadSize: Int, val placeholdersEnabled: Boolean) {
//        abstract val key: Key?
//        class Refresh<Key : Any> constructor(override val key: Key?, loadSize: Int, placeholdersEnabled: Boolean)
//            : LoadParams<Key>(loadSize = loadSize, placeholdersEnabled = placeholdersEnabled)
//
//        class Append<Key : Any> constructor(override val key: Key, loadSize: Int, placeholdersEnabled: Boolean)
//            : LoadParams<Key>(loadSize = loadSize, placeholdersEnabled = placeholdersEnabled)
//
//        class Prepend<Key : Any> constructor(override val key: Key, loadSize: Int, placeholdersEnabled: Boolean)
//            : LoadParams<Key>(loadSize = loadSize, placeholdersEnabled = placeholdersEnabled)
//
//        internal companion object {
//            fun <Key : Any> create(loadType: LoadType, key: Key?, loadSize: Int, placeholdersEnabled: Boolean)
//            : LoadParams<Key> = when (loadType) {
//                LoadType.REFRESH -> Refresh(key = key, loadSize = loadSize, placeholdersEnabled = placeholdersEnabled,)
//                LoadType.PREPEND -> Prepend(loadSize = loadSize, key = requireNotNull(key) { "key cannot be null for prepend" }, placeholdersEnabled = placeholdersEnabled)
//                LoadType.APPEND -> Append(loadSize = loadSize, key = requireNotNull(key) { "key cannot be null for append" }, placeholdersEnabled = placeholdersEnabled)
//            }
//        }
//    }
//
//    sealed class LoadResult<Key : Any, Value : Any> {
//        data class Error<Key : Any, Value : Any>(val throwable: Throwable) : LoadResult<Key, Value>()
//
//        data class Page<Key : Any, Value : Any> constructor(
//            val data: List<Value>,
//            val prevKey: Key?,
//            val nextKey: Key?,
//            @IntRange(from = COUNT_UNDEFINED.toLong())
//            val itemsBefore: Int = COUNT_UNDEFINED,
//            @IntRange(from = COUNT_UNDEFINED.toLong())
//            val itemsAfter: Int = COUNT_UNDEFINED
//        ) : LoadResult<Key, Value>() {
//            constructor(data: List<Value>, prevKey: Key?, nextKey: Key?) : this(data, prevKey, nextKey, COUNT_UNDEFINED, COUNT_UNDEFINED)
//            init {
//                require(itemsBefore == COUNT_UNDEFINED || itemsBefore >= 0) {
//                    "itemsBefore cannot be negative"
//                }
//
//                require(itemsAfter == COUNT_UNDEFINED || itemsAfter >= 0) {
//                    "itemsAfter cannot be negative"
//                }
//            }
//
//            companion object {
//                const val COUNT_UNDEFINED = Int.MIN_VALUE
//
//                @Suppress("MemberVisibilityCanBePrivate") // Prevent synthetic accessor generation.
//                internal val EMPTY = Page(emptyList(), null, null, 0, 0)
//
//                @Suppress("UNCHECKED_CAST") // Can safely ignore, since the list is empty.
//                internal fun <Key : Any, Value : Any> empty() = EMPTY as Page<Key, Value>
//            }
//        }
//    }
//
//    open val jumpingSupported: Boolean
//        get() = false
//
//    open val keyReuseSupported: Boolean
//        get() = false
//
//    open fun getRefreshKey(state: PagingState<Key, Value>): Key? = null
//
//    private val onInvalidatedCallbacks = CopyOnWriteArrayList<() -> Unit>()
//
//    private val _invalid = AtomicBoolean(false)
//
//    val invalid: Boolean
//        get() = _invalid.get()
//
//    fun invalidate() {
//        if (_invalid.compareAndSet(false, true)) {
//            onInvalidatedCallbacks.forEach { it.invoke() }
//        }
//    }
//
//    fun registerInvalidatedCallback(onInvalidatedCallback: () -> Unit) {
//        onInvalidatedCallbacks.add(onInvalidatedCallback)
//    }
//
//    fun unregisterInvalidatedCallback(onInvalidatedCallback: () -> Unit) {
//        onInvalidatedCallbacks.remove(onInvalidatedCallback)
//    }
//
//    abstract suspend fun load(params: LoadParams<Key>): LoadResult<Key, Value>
//}
