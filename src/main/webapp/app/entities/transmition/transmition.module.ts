import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BroadcastSharedModule } from 'app/shared/shared.module';
import { TransmitionComponent } from './transmition.component';
import { TransmitionDetailComponent } from './transmition-detail.component';
import { TransmitionUpdateComponent } from './transmition-update.component';
import { TransmitionDeleteDialogComponent } from './transmition-delete-dialog.component';
import { transmitionRoute } from './transmition.route';

@NgModule({
  imports: [BroadcastSharedModule, RouterModule.forChild(transmitionRoute)],
  declarations: [TransmitionComponent, TransmitionDetailComponent, TransmitionUpdateComponent, TransmitionDeleteDialogComponent],
  entryComponents: [TransmitionDeleteDialogComponent]
})
export class BroadcastTransmitionModule {}
