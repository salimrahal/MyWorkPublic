
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[List[Task],Form[Task],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(tasks: List[Task], taskForm: Form[Task]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.43*/("""

"""),format.raw/*4.1*/("""
"""),_display_(Seq[Any](/*5.2*/main("Todo list")/*5.19*/ {_display_(Seq[Any](format.raw/*5.21*/("""
 
    <h2></h2>
     """),_display_(Seq[Any](/*8.7*/form(routes.Application.show())/*8.38*/ {_display_(Seq[Any](format.raw/*8.40*/("""
        <input type="submit" value="Welcome">
        
    """)))})),format.raw/*11.6*/("""
    
""")))})))}
    }
    
    def render(tasks:List[Task],taskForm:Form[Task]): play.api.templates.HtmlFormat.Appendable = apply(tasks,taskForm)
    
    def f:((List[Task],Form[Task]) => play.api.templates.HtmlFormat.Appendable) = (tasks,taskForm) => apply(tasks,taskForm)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Oct 20 11:57:47 EEST 2013
                    SOURCE: /home/salim/workspace_dec/workspace/tfcassignement/app/views/index.scala.html
                    HASH: d7e9a475b5741ffe90ac6a1161ef07359b6bed36
                    MATRIX: 789->1|940->42|968->61|1004->63|1029->80|1068->82|1125->105|1164->136|1203->138|1295->199
                    LINES: 26->1|30->1|32->4|33->5|33->5|33->5|36->8|36->8|36->8|39->11
                    -- GENERATED --
                */
            