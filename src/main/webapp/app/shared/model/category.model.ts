import { ITransmition } from 'app/shared/model/transmition.model';

export interface ICategory {
  id?: number;
  name?: string;
  ids?: ITransmition[];
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public ids?: ITransmition[]) {}
}
