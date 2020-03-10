import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'channel',
        loadChildren: () => import('./channel/channel.module').then(m => m.BroadcastChannelModule)
      },
      {
        path: 'day',
        loadChildren: () => import('./day/day.module').then(m => m.BroadcastDayModule)
      },
      {
        path: 'calendar',
        loadChildren: () => import('./calendar/calendar.module').then(m => m.BroadcastCalendarModule)
      },
      {
        path: 'transmition',
        loadChildren: () => import('./transmition/transmition.module').then(m => m.BroadcastTransmitionModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.BroadcastCategoryModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BroadcastEntityModule {}
