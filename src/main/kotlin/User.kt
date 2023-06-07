import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id:EntityID<Int>):IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    var cityId by Users.cityId
    //項要資料都用字串處理的話
//    var cityId by Users.cityId.transform({it.toInt()},{it.toString()})
    var name by Users.name
}