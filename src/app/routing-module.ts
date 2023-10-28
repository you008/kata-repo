import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {Statement} from "@angular/compiler";
import {StatementsComponent} from "./statements/statements.component";
import {DepositComponent} from "./deposit/deposit.component";


const appRoute : Routes = [
  {path:'', redirectTo : 'statements', pathMatch:'full'},
  {path : 'statements' , component: StatementsComponent},
  {path : 'deposit' , component: DepositComponent},
]
@NgModule(
  {
    imports :[RouterModule.forRoot(appRoute)],
    exports :[RouterModule],
    providers :[]
  }
)
export class RoutingModule {

}
