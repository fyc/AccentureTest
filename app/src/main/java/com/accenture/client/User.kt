package com.accenture.client

class BookingData {
    var id: Int? = null
    var originAndDestinationPair: String? = null

    constructor()
    constructor(id: Int?, name: String?) {
        this.id = id
        this.originAndDestinationPair = name
    }

    override fun toString(): String {
        return "BookingData{id=$id, originAndDestinationPair='$originAndDestinationPair'}"
    }
}
