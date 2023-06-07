import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>) {
    //DSL
    /*
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction {
        //將SQL印出來
//        addLogger(StdOutSqlLogger)
        //建Cities表
        SchemaUtils.create(Cities)
        Cities.insert {
            it[name] = "TaiChung"
        }

        val id = Cities.insertAndGetId {
            it[name] = "Taiwan"
        }

        printAllData()
        updateWhereId(id, "Taipei")
        printDataById(id)
        deleteWhereId(id)
        println(Cities.selectAll().count())

        val dataList = listOf("新竹", "桃園", "苗栗")
        batchInsert(dataList)
        println(Cities.selectAll().count())
        getDataFromAndOffset(2, 1)


//        Cities.selectAll()
//            .orderBy(Cities.id to SortOrder.DESC)
//            .forEach {
//                println("Cities #${it[Cities.id]} : ${it[Cities.name]}")
//            }
        }
*/
//-------------------------------------------------------------------//
//        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
//        transaction{
//            SchemaUtils.create(Cities)
//            SchemaUtils.create(Users)
//
//            val dataList = listOf("新竹", "桃園", "苗栗")
//            val users = listOf(
//                mapOf("name" to "Alice","cityId" to "1"),
//                mapOf("name" to "Kevin","cityId" to "2"),
//                mapOf("name" to "Bob","cityId" to "2"),
//                mapOf("name" to "Dave","cityId" to "3"),
//                mapOf("name" to "Eve","cityId" to "3"),
//            )
//            Cities.batchInsert(dataList){
//                name -> this[Cities.name] = name
//            }
//            Users.batchInsert(users){ user ->
//                user["name"]?.let{this[Users.name] = it}
//                user["cityId"]?.let{this[Users.cityId]= it.toInt()}
//            }
//
//            Users.join(Cities,
//                JoinType.INNER,
//                additionalConstraint = {Users.cityId eq Cities.id}
//            ).selectAll()
//                .forEach{
//                    println("${it[Users.name]}: ${it[Cities.name]}")
//                }
//
//        }
//----------------------------------------------------------------//
    //Dao
    test()
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    transaction {
        SchemaUtils.create(Users)
        //新增
        User.new{
            cityId = 1
            name = "Alice"
        }
        User.new{
            cityId = 2
            name = "Kevin"

        }
       printDaoUser()
        //修改
        val user = User.new {
            cityId = 2
            name = "Bob"
        }
        user.name = "Carol"
//        val user2 = User[2]
        printDaoUser()

        User[3].delete()

        println(User.findById(2)?.name)

        User.all()
            .sortedByDescending { it.name }
            .forEach{
                println("id: ${it.id} ,name: ${it.name}")
            }
    }
}
fun test() {

    var discountPercentage = 20
    discountPercentage++
    val item = "Google Chromecast"
    val offer = "Sale  - Up to $discountPercentage% discount off $item! Hurry Up!"

    println(offer)
}
fun printDaoUser(){
    User.all()
        .forEach{
            println("id: ${it.id} ,name: ${it.name}")
        }
}


fun getDataFromAndOffset(from:Int,offset:Long){
    Cities.selectAll()
        .limit(from, offset)
        .forEach {
            println("Cities #${it[Cities.id]} : ${it[Cities.name]}")
        }
}

fun printDataById(id: EntityID<Int>) {
    Cities.select(Cities.id eq id)
        .forEach {
            println("Cities #${it[Cities.id]} : ${it[Cities.name]}")
        }
}

fun printAllData(){
    Cities.selectAll()
        .forEach {
            println("Cities #${it[Cities.id]} : ${it[Cities.name]}")
        }
}

//指定ID更新
fun updateWhereId(id: EntityID<Int>, name: String) {
    Cities.update({ Cities.id eq id }) {
        it[this.name] = name
    }
}

//指定ID刪除
fun deleteWhereId(id: EntityID<Int>) {
    Cities.deleteWhere { Cities.id eq id }
}

//多項新增
fun batchInsert(dataList: List<String>) {
    Cities.batchInsert(dataList) { name ->
        this[Cities.name] = name
    }
}

object Cities : IntIdTable() {
    val name = varchar("name", 50)
}

object Users : IntIdTable() {
    val cityId = integer("cityId")
    val name = varchar("name", 50)
}