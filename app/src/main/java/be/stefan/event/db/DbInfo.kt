package be.stefan.event.db

class DbInfo {
    companion object {
        public val DB_NAME : String = "event"
        public val DB_VERSION : Int = 1

        public val TABLE_NAME : String = "list"
        public val COLUMN_ID : String = "_id"
        public val COLUMN_TITLE : String = "title"
        public val COLUMN_TIME : String = "time"
        public val COLUMN_DESC : String = "desc"
        public val COLUMN_ADDRESS : String = "address"

        public val REQUEST_CREATE : String = """
            CREATE TABLE $TABLE_NAME (
                ${this.TABLE_NAME}.${this.COLUMN_ID} = INTEGER PRIMARY KEY AUTOINCREMENT,
                ${this.TABLE_NAME}.${this.COLUMN_TITLE} = TEXT NOT NULL,
                ${this.TABLE_NAME}.${this.COLUMN_TIME} = TEXT NOT NULL,
                ${this.TABLE_NAME}.${this.COLUMN_DESC} = TEXT,
                ${this.TABLE_NAME}.${this.COLUMN_ADDRESS} = TEXT
            )"""

        public val REQUEST_DELETE : String = """
            DROP TABLE ${this.TABLE_NAME};
            """
    }

}