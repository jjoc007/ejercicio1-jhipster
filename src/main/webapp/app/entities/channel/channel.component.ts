import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IChannel } from 'app/shared/model/channel.model';
import { ChannelService } from './channel.service';
import { ChannelDeleteDialogComponent } from './channel-delete-dialog.component';

@Component({
  selector: 'jhi-channel',
  templateUrl: './channel.component.html'
})
export class ChannelComponent implements OnInit, OnDestroy {
  channels?: IChannel[];
  eventSubscriber?: Subscription;

  constructor(protected channelService: ChannelService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.channelService.query().subscribe((res: HttpResponse<IChannel[]>) => (this.channels = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInChannels();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IChannel): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInChannels(): void {
    this.eventSubscriber = this.eventManager.subscribe('channelListModification', () => this.loadAll());
  }

  delete(channel: IChannel): void {
    const modalRef = this.modalService.open(ChannelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.channel = channel;
  }
}
