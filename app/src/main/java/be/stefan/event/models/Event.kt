package be.stefan.event.models

class Event {

    var id : Long
    var title : String
    var desc : String
    var time : String
    var address : String

    constructor(id : Long, title : String, desc : String, time : String , address: String) {
        this.id = id
        this.title = title
        this.desc = desc
        this.time = time
        this.address = address
    }

}