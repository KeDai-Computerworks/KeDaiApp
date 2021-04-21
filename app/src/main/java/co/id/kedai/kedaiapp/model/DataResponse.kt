package co.id.kedai.kedaiapp.model

data class DataResponse(
    val data: ArrayList<DataResult>,
    val meta: DataMeta
)

data class DataResult(
    val id: Int,            //blog,ebook,event
    val title: String,      //blog,ebook,event
    val category: String,   //blog,ebook
    val author: String,     //blog,ebook,event
    val thumbnail: String,  //blog,ebook,event
    val path: String,       //ebook
    val created_at: String, //blog,ebook,event
    val content: String,    //blog,event
    val location: String,   //event
    val date: String        //event
)

data class DataMeta(
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val path: String,
    val per_page: Int,
    val to: Int,
    val total: Int
)


