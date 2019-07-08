export class Cloud {

  constructor(
    public id: number,
    public image: string = 'https://zupimages.net/up/19/26/afo4.png',
    public date_created: string = '1990-02-12',
    public name: string = '',
    public entities,
    public parentEntity: string
  ) {}
}
