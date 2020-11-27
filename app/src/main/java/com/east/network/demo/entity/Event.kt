/**
 *
 */
package com.hb.travel.entity

import java.io.Serializable

class Event : Serializable {
    var id = 0
    var title: String? = null
    var content: String? = null
    var startTime: Long = 0
    var endTime: Long = 0
    var image: String? = null
    var type: Int = 0

    //-1.未完善,1:进行中,2:等待开启,3:已结束
    var state: Int = 0
    var icon: String? = null
    var remark: String? = null
    var link: String? = null

    /**
     * App类型，1：生活馆
     */
    var appType: Int = 0

    companion object {
        /**
         *
         */
        private const val serialVersionUID = -4612263142313060376L
    }
}