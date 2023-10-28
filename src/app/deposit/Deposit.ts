export class Deposit {

  public id : number | null;
  public amount : number;
  public date : Date;
  public description: string;

  constructor(id: number, amount: number, date: Date, description: string) {
    this.id = id;
    this.amount = amount;
    this.date = date;
    this.description = description;
  }


}
