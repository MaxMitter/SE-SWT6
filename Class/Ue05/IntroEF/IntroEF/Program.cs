using IntroEF.Dal;
using IntroEF.Logic;
using IntroEF.Utils;

Console.WriteLine("IntroEF");

PrintUtil.PrintTitle("Create DB Schema");

using (var db = new OrderManagementContext())
{
    await DatabaseUtil.CreateDatabaseAsync(db, recreate: true);
}

PrintUtil.PrintTitle("Add Customers");
await Commands.AddCustomersAsync();

PrintUtil.PrintTitle("List Customers");
await Commands.ListCustomersAsync();

PrintUtil.PrintTitle("Add Orders");
await Commands.AddOrdersToCustomerAsync();

PrintUtil.PrintTitle("Print Orders");
await Commands.ListCustomersAsync();
