import { ICalendar } from 'app/shared/model/calendar.model';
import { ICategory } from 'app/shared/model/category.model';

export interface ITransmition {
  id?: number;
  name?: string;
  calendars?: ICalendar[];
  ids?: ICategory[];
}

export class Transmition implements ITransmition {
  constructor(public id?: number, public name?: string, public calendars?: ICalendar[], public ids?: ICategory[]) {}
}
