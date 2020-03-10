import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDay } from 'app/shared/model/day.model';
import { DayService } from './day.service';
import { DayDeleteDialogComponent } from './day-delete-dialog.component';

@Component({
  selector: 'jhi-day',
  templateUrl: './day.component.html'
})
export class DayComponent implements OnInit, OnDestroy {
  days?: IDay[];
  eventSubscriber?: Subscription;

  constructor(protected dayService: DayService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.dayService.query().subscribe((res: HttpResponse<IDay[]>) => (this.days = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDays();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDay): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDays(): void {
    this.eventSubscriber = this.eventManager.subscribe('dayListModification', () => this.loadAll());
  }

  delete(day: IDay): void {
    const modalRef = this.modalService.open(DayDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.day = day;
  }
}
