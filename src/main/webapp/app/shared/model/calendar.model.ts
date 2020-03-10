import { IDay } from 'app/shared/model/day.model';
import { ITransmition } from 'app/shared/model/transmition.model';

export interface ICalendar {
  id?: number;
  initialHour?: string;
  finalHour?: string;
  days?: IDay[];
  transmitions?: ITransmition;
}

export class Calendar implements ICalendar {
  constructor(
    public id?: number,
    public initialHour?: string,
    public finalHour?: string,
    public days?: IDay[],
    public transmitions?: ITransmition
  ) {}
}
