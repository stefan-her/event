package be.stefan.event.models



class Event {

    var id : Long? = null
    var title : String
    var desc : String
    var time : String
    var address : String

    constructor(id: Long? = null, title: String, desc: String, time: String, address: String) {
        if (id != null) { this.id = id }
        this.title = title
        this.desc = desc
        this.time = time
        this.address = address
    }




}