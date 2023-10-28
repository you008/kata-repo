import { Component } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatTooltipModule} from '@angular/material/tooltip';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {DepositService} from "./DepositService";
import {Deposit} from "./Deposit";
import {Router} from "@angular/router";
@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css'],
  standalone: true,
  imports: [MatFormFieldModule, MatInputModule, MatButtonModule,
    MatIconModule, MatTooltipModule, FormsModule,
    MatSelectModule, NgForOf],
})
export class DepositComponent {
  amount: number = 0;
  options: string [] =['Apple pay', 'ByPal'];
  selectedMeanPayment: string = "";
  description: string = "";
  constructor(private  depositService : DepositService,  private router: Router) {
  }
  addDeposit() {
    const deposit : Deposit = {
      id:null,
      date: new Date(),
      amount : this.amount,
      description: this.selectedMeanPayment
    }
    this.depositService.storeDeposit(deposit);
    this.router.navigate(['/statements'])
  }
}
