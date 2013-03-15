package net.liftmodules.combobox

import net.liftweb.json._
import net.liftweb.json.JsonParser
import net.liftweb.json.Serialization

/**
 *  This class is used to parse JSON returned by select2
 *  when user selected / unselected an item from it.
 *
 *  @param  newItemPrefix   The prefix string for new item generated by users.
 */
class DataParser(newItemPrefix: String) {
  
  private implicit val formats = DefaultFormats

  /**
   *  The data-type represented the selection in single-value combobox.
   *
   *   - The first element in the tuple is the selected item.
   *   - The second element in the tuple indicate whether this item is a 
   *     new item generated by user.
   */
  type Selected = Option[(ComboItem, Boolean)]

  /**
   *  Parse JSON data from select2.
   *
   *  This method will parse JSON returned by select2 to Scala object.
   *
   *  If the combo box is allow multiple value, then it will return a
   *  Scala's `Left[List[ComboItem]]` which contains the item that user
   *  selected.
   *
   *  If the combo box is single value, then it will return 
   *  `Some[(selectedItem, isNewItem)]` if there is value been selected or
   *  it will return None if user unselected current value.
   *
   *  @param  jsonString  The JSON data returned by select2.
   *  @return             The corresponding Scala object.
   */
  def parseSelected(jsonString: String): Either[List[ComboItem], Selected] = {

    JsonParser.parse(jsonString) match {
      case JArray(items) => Left(items.map(_.extract[ComboItem]).toList)
      case JNull => Right(None)
      case jsonObject: JObject =>
        
        val comboItem = jsonObject.extract[ComboItem]
        val isNewItem = comboItem.id.startsWith(newItemPrefix)

        Right(Some(comboItem, isNewItem))

      case _ => throw new Exception("Don't know what you passed:" + jsonString)
    }

  }

}

