import {Injectable, OnInit} from "@angular/core";
import {Deposit} from "./Deposit";
import {HttpClient, HttpParams} from '@angular/common/http';
import {catchError, map, Subject, throwError} from "rxjs";

@Injectable({ providedIn: 'root' })
export class DepositService implements OnInit {
  error = new Subject<string>();
  constructor(private http: HttpClient) {}
  ngOnInit(): void {

  }
  fetchDeposits(start: string, end: string) {
    let searchParams = new HttpParams();
    searchParams = searchParams.append('begin', start);
    searchParams = searchParams.append('end', end);

    return this.http
      .get<{ [id: number]: Deposit }>(
        'http://localhost:8080/deposit',
        {
          params: searchParams,
          responseType: 'json'
        }
      )
      .pipe(
        map(responseData => {
          const postsArray: Deposit[] = [];
          for (const key in responseData) {
              postsArray.push({ ...responseData[key], id: +key });
          }
          return postsArray;
        }),
        catchError(errorRes => {
          // Send to analytics server
          return throwError(errorRes);
        })
      );
  }

  storeDeposit(deposit: Deposit) {
    this.http
      .post<{ name: string }>(
        'http://localhost:8080/deposit',
        deposit,
        {
          observe: 'response'
        }
      )
      .subscribe(
        responseData => {
          console.log(responseData);
        },
        error => {
          this.error.next(error.message);
        }
      );
  }
}
