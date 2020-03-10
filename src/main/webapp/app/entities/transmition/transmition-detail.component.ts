import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITransmition } from 'app/shared/model/transmition.model';

@Component({
  selector: 'jhi-transmition-detail',
  templateUrl: './transmition-detail.component.html'
})
export class TransmitionDetailComponent implements OnInit {
  transmition: ITransmition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transmition }) => (this.transmition = transmition));
  }

  previousState(): void {
    window.history.back();
  }
}
