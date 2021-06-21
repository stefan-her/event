package be.stefan.event.models
import java.time.LocalDateTime

class Event {

    var title : String
    var description : String
    var time : LocalDateTime
    var grp : Int

    constructor( title : String, description : String, time : LocalDateTime , grp : Int) {
        this.title = title
        this.description = description
        this.time = time
        this.grp = grp
    }

}