package be.stefan.event.models

class Event {


    var title : String
    var desc : String
    var time : String
    var address : String

    constructor( title : String, desc : String, time : String , address: String) {
        this.title = title
        this.desc = desc
        this.time = time
        this.address = address
    }

}