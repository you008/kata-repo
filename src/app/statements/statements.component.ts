import {Component, OnInit} from '@angular/core';
import {DepositService} from "../deposit/DepositService";
import {Deposit} from "../deposit/Deposit";
import {HttpClient} from "@angular/common/http";
import {MatTableModule} from '@angular/material/table';
import {HeaderComponent} from "../header/header.component";
import {MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatNativeDateModule} from '@angular/material/core';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from "@angular/material/tooltip";
import {DatePipe} from "@angular/common";
@Component({
  selector: 'app-statements',
  templateUrl: './statements.component.html',
  styleUrls: ['./statements.component.css'],
  standalone: true,
  imports: [MatTableModule, HeaderComponent,MatFormFieldModule,
    MatInputModule, MatDatepickerModule, MatNativeDateModule,
    MatButtonModule, MatTooltipModule, MatIconModule],
})
export class StatementsComponent implements OnInit{

  startDate : string | null | undefined;
  endDate: string | null | undefined;
  dateFormat: string = 'ddMMYYYY' ;
  deposits : Deposit[] = [];
  displayedColumns: string[] = ['id', 'description', 'amount', 'date', 'balance'];
  datepipe: DatePipe = new DatePipe('en-US');
  constructor(private http: HttpClient, private  depositService: DepositService) {
  }
  ngOnInit(): void {

  }
  searchStatemets() {
    if(!this.startDate) {
      this.startDate = "";
    }
    if(!this.endDate) {
      this.endDate = "";
    }
    this.depositService.fetchDeposits(this.startDate, this.endDate).subscribe(deposits => {
      this.deposits  = deposits;
      console.log(deposits);
    })
  }
  dateChangeStart( $event: MatDatepickerInputEvent<string>) {
    this.startDate = $event.value;
    this.startDate = this.datepipe.transform(this.startDate, this.dateFormat)
  }
  dateChangeEnd( $event: MatDatepickerInputEvent<string>) {
    this.endDate = $event.value;
    this.endDate = this.datepipe.transform(this.endDate, this.dateFormat)
  }

}
