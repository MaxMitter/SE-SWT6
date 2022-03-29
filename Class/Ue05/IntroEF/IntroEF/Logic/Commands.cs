using IntroEF.Dal;
using IntroEF.Domain;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IntroEF.Logic;
internal class Commands
{
    public static async Task AddCustomersAsync()
    {
        using var db = new OrderManagementContext();
        var customer1 = new Customer("Mayr Immo", Rating.A)
        {
            Address = new(4232, "Hagenberg", "Softwarepark 11")
        };
        var customer2 = new Customer("Mayr Invest", Rating.B);
        var customer3 = new Customer("Mayr Bank", Rating.C);
        await db.Customers.AddRangeAsync(customer1, customer2, customer3);
        await db.SaveChangesAsync();
    }

    public static async Task ListCustomersAsync()
    {
        using var db = new OrderManagementContext();
        var list = await db.Customers
                           .AsNoTracking()
                           //.Include(c => c.Address) not needed with embedded
                           .Include(c => c.Orders)
                           .ToListAsync();
        foreach (var customer in list)
        {
            Console.WriteLine(customer);
            if (customer.Address is not null)
                Console.WriteLine(customer.Address);
            if (customer.Orders.Count > 0)
            {
                Console.WriteLine("-----Orders-----");
                foreach(var order in customer.Orders)
                    Console.WriteLine(order);
            }
        }
    }

    public static async Task AddOrdersToCustomerAsync()
    {
        using var db = new OrderManagementContext();

        var customer = await db.Customers.OrderBy(c => c.Id).FirstOrDefaultAsync();

        if (customer is null)
            return;

        var order1 = new Order("Gamer", DateTime.Now, 2500m)
        {
            Customer = customer,
        };

        var order2 = new Order("Office", DateTime.Now.AddDays(5), 800m)
        {
            Customer = customer,
        };

        await db.Orders.AddRangeAsync(order1, order2);
        await db.SaveChangesAsync();
    }
}
