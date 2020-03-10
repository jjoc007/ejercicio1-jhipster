import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransmition } from 'app/shared/model/transmition.model';
import { TransmitionService } from './transmition.service';
import { TransmitionDeleteDialogComponent } from './transmition-delete-dialog.component';

@Component({
  selector: 'jhi-transmition',
  templateUrl: './transmition.component.html'
})
export class TransmitionComponent implements OnInit, OnDestroy {
  transmitions?: ITransmition[];
  eventSubscriber?: Subscription;

  constructor(
    protected transmitionService: TransmitionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.transmitionService.query().subscribe((res: HttpResponse<ITransmition[]>) => (this.transmitions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransmitions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransmition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransmitions(): void {
    this.eventSubscriber = this.eventManager.subscribe('transmitionListModification', () => this.loadAll());
  }

  delete(transmition: ITransmition): void {
    const modalRef = this.modalService.open(TransmitionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transmition = transmition;
  }
}
