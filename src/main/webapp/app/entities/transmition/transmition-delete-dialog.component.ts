import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransmition } from 'app/shared/model/transmition.model';
import { TransmitionService } from './transmition.service';

@Component({
  templateUrl: './transmition-delete-dialog.component.html'
})
export class TransmitionDeleteDialogComponent {
  transmition?: ITransmition;

  constructor(
    protected transmitionService: TransmitionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transmitionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transmitionListModification');
      this.activeModal.close();
    });
  }
}
